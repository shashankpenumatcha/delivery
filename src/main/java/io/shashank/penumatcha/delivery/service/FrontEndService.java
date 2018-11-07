package io.shashank.penumatcha.delivery.service;

import io.shashank.penumatcha.delivery.domain.FcmToken;
import io.shashank.penumatcha.delivery.domain.Product;
import io.shashank.penumatcha.delivery.domain.UserProfile;
import io.shashank.penumatcha.delivery.repository.CartItemsRepository;
import io.shashank.penumatcha.delivery.repository.CartRepository;
import io.shashank.penumatcha.delivery.repository.FcmTokenRepository;
import io.shashank.penumatcha.delivery.repository.UserProfileRepository;
import io.shashank.penumatcha.delivery.web.rest.errors.BadRequestAlertException;
import io.undertow.util.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class FrontEndService {

    private final Logger log = LoggerFactory.getLogger(FrontEndService.class);

    private final UserService userService;

    private final UserProfileRepository userProfileRepository;

    private final CartRepository cartRepository;

    private final CartItemsRepository cartItemsRepository;

    private final FcmTokenRepository fcmTokenRepository;

    public FrontEndService(UserService userService, UserProfileRepository userProfileRepository,
                           CartRepository cartRepository,CartItemsRepository cartItemsRepository,
                           FcmTokenRepository fcmTokenRepository) {
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.cartRepository=cartRepository;
        this.cartItemsRepository=cartItemsRepository;
        this.fcmTokenRepository = fcmTokenRepository;
    }

    public UserProfile getCurrentUserProfile() {
        String login = userService.getCurrentUserLogin();
        UserProfile userProfile = userProfileRepository.findUserProfileByLogin(login);
        return userProfile;

    }

    //checks if product is active,and quantity requirements are met
    public Map<String, Object> checkProductAvailability(Float quantity, Product product) {
        Map response = new HashMap<String, Object>();
        if (!product.isActive()) {
            log.debug("product not active: {}");
            response.put("error", "Product not active");
        }
        if (product.getQuantity() == null || product.getQuantity() <= 0) {
            log.debug("Product not available in inventory {}");
            response.put("error", "Product not available in inventory");
        }
        log.debug("fetching products minimum quantity : {}");
        if (product.getMinimumQuantity() != null) {
            log.debug("Checking if minim quantity requirement is met: {}");
            log.debug(quantity.toString());
            log.debug(product.getMinimumQuantity().toString());
            log.debug(product.getQuantity().toString());

            if (quantity >= product.getMinimumQuantity()
                && quantity <= product.getQuantity()) {
                log.debug("Minimum quantity requirement met: {}");
                response.put("quantity", quantity);
            } else {
                response.put("error", "Please try increasing or decreasing the quantity");
            }
        } else {
            log.debug("product doesn't have minimum quantity set, hence checking inventory if product is available: {}");
            if (quantity <= product.getQuantity()) {
                log.debug("product available : {}");
                response.put("quantity", quantity);
            } else {
                log.debug("Quantity requirements not met to add to cart for login, reduce quantity: {}");
                response.put("error", "Please try reducing the quantity");
            }
        }
        return response;
    }


    public void sendFCM (String title, String body, String to) {

            HttpHeaders headers = this.makeHeaders(false);
            final String uri = "https://fcm.googleapis.com/fcm/send";

            RestTemplate restTemplate = new RestTemplate();
            Map<String,Object> payload = new HashMap<String,Object>();
            Map<String,String> notification = new HashMap<String,String>();

            notification.put("title",title);
            notification.put("body", body);
            payload.put("notification",notification);
            payload.put("to",to);
            HttpEntity<Object> request = new HttpEntity<Object>(payload,headers);

                ResponseEntity<String>  result = restTemplate.postForEntity(uri,request, String.class);

        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonMap = null;
        jsonMap = jsonParser.parseMap(result.getBody());
    }

    public HttpHeaders makeHeaders(boolean withProjectId){
        log.debug(">>>>>>>>>>>>>>>>>> creating http headers for fcm notifications");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","key=AAAAZPImt-w:APA91bHAsCOE6SLk6idkGOm_QhTrXdE9SlVczLRgn6waMk3-P2Z_juXeqhFFyiiw6RE7qP5c7f3Mcn6fHORfU1S414zMLjAvGu_Wg5TBOfjd9AO-9ZR43kFGL_H49iIUjXFbiWSvAMaC");
        headers.add("Content-Type", "application/json");
        if(withProjectId){
            headers.add("project_id", "433559353324");
        }
        return headers;
    }

    public String getFcmGroupToken(HttpHeaders headers, Long userProfileId){
        log.debug(">>>>>>>>>>>>>>>>>>getting group notification key for user "+ userProfileId);
        String notificationKey = null;
        JsonParser jsonParser = new BasicJsonParser();
        try {
            final String uri = "https://fcm.googleapis.com/fcm/notification?notification_key_name=" + userProfileId.toString();
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Object> request = new HttpEntity<>(headers);
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
            Map<String, Object> jsonMapPre = null;
            jsonMapPre = jsonParser.parseMap(result.getBody());
            if (jsonMapPre.containsKey("notification_key")) {
                log.debug(">>>>>>>>>>>>>>>>>>found existing group notification key for user "+ userProfileId);
                notificationKey = jsonMapPre.get("notification_key").toString();
            }
        }catch (Exception e){
            log.debug(">>>>>>>>>>>>>>>>>>group notification key not found for user "+ userProfileId);
        }finally{
            return notificationKey;
        }
    }

    public String updateGroupToken(HttpHeaders headers, Long userProfileId, String operation, String key, List<String> ids){
        log.debug(">>>>>>>>>>>>>>>>>>getting group notification key for user "+ userProfileId);
        String notificationKey = null;
        JsonParser jsonParser = new BasicJsonParser();
        try {
            final String uri = "https://fcm.googleapis.com/fcm/notification";
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> body = new HashMap<String, Object>();
            if(operation=="add" || operation=="remove"){
                body.put("notification_key", key);
            }
            body.put("registration_ids", ids);
            body.put("operation", operation);
            body.put("notification_key_name", userProfileId.toString());
            HttpEntity<Object> request = new HttpEntity<Object>(body, headers);
            log.debug(">>>>>>>>>>>>>>>>>> sending http request to "+operation+" from  group");
            log.debug(">>>>>>>>>>>>>>>>>> sending http request to "+ body.toString());

            ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
            log.debug(">>>>>>>>>>>>>>>>>>  http result for "+operation+" group>>>>>>>>>>>>>> " + result);
            Map<String, Object> jsonMap = null;
            jsonMap = jsonParser.parseMap(result.getBody());
            if (jsonMap == null) {
                log.debug(">>>>>>>>>>>>>>>>>> null returned when "+operation+" for previous users group"+ userProfileId);
                return null;
            }
            if (!jsonMap.containsKey("notification_key")) {
                log.debug(">>>>>>>>>>>>>>>>>> no notification key was returned while"+operation+" group for user "+ userProfileId);
            }
            notificationKey = jsonMap.get("notification_key").toString();

            }catch (Exception e){
                log.debug(">>>>>>>>>>>>>>>>>>group notification key not found for user "+ userProfileId + operation);
            }finally{
                return notificationKey;
            }
    }


    public String updateFcmToken(String token) {
        log.debug(">>>>>>>>>>>>>>>>>> Request to update fcm token");
        JsonParser jsonParser = new BasicJsonParser();
        HttpHeaders headers = makeHeaders(true);
        if(token == null){return null;}
        UserProfile userProfile = this.getCurrentUserProfile();
        log.debug(">>>>>>>>>>>>>>>>>> fetching existing fcm token by token");
        FcmToken existingToken = fcmTokenRepository.findOneByToken(token);
        if(existingToken!=null && existingToken.getUserProfile().getId() == userProfile.getId()){
            log.debug(">>>>>>>>>>>>>>>>>> fcm token already exists for current user");
            return "success";
        }
        if(existingToken!=null){
            log.debug(">>>>>>>>>>>>>>>>>> fcm token exists for another user" + existingToken.getUserProfile().getId().toString());
            Long previousUserId = existingToken.getUserProfile().getId();
            UserProfile previousUserProfile = existingToken.getUserProfile();
            log.debug(">>>>>>>>>>>>>>>>>> deleting fcm token for another user from db");
            fcmTokenRepository.delete(existingToken);
            try {
                String previousUserGroupNotificationKey = this.getFcmGroupToken(headers,previousUserId);
                if (previousUserGroupNotificationKey != null) {
                    log.debug(">>>>>>>>>>>>>>>>>> deleting fcm token for another user from group");
                    List<String> registrationIds = new ArrayList<String>();
                    registrationIds.add(token);
                    previousUserGroupNotificationKey = this.updateGroupToken(headers,previousUserId,"remove",previousUserGroupNotificationKey,registrationIds);
                    if(previousUserGroupNotificationKey!=null){
                        userProfile.setFcmToken(previousUserGroupNotificationKey.toString());
                        userProfileRepository.save(previousUserProfile);
                        log.debug(">>>>>>>>>>>>>>>>>> previous user profile updated with new notification key");
                    }else{
                        log.debug(">>>>>>>>>>>>>>>>>> something unexpected happened while deleting token from group for user" + previousUserId );
                    }
                }
            }catch (Exception e){
                log.debug(">>>>>>>>>>>>>>>>>>>>>>> no token found for previous user" );
            }
            log.debug(">>>>>>>>>>>>>>>>>> deleted fcm token for another user");
        }
        FcmToken newToken = new FcmToken();
        newToken.setToken(token);
        newToken.setUserProfile(userProfile);
        try{
            log.debug(">>>>>>>>>>>>>>>>>> saving fcm token for user+"+ newToken.getUserProfile().getId().toString());
            newToken  = fcmTokenRepository.save(newToken);
        }catch (Exception e){
            log.debug(">>>>>>>>>>>>>>>>>> error while saving token for user" + newToken.getUserProfile().getId().toString());
            return null;
        }
        log.debug(">>>>>>>>>>>>>>>>>> saved fcm token for user" + newToken.getUserProfile().getId().toString());
        log.debug(">>>>>>>>>>>>>>>>>> getting existing group notification key for user"+ newToken.getUserProfile().getId().toString());
        String groupNotificationKey = null;
        try {
            groupNotificationKey = this.getFcmGroupToken(headers,userProfile.getId());
        } catch(Exception e){
            log.debug(">>>>>>>>>>>>>>>>>> error while getting existing group notification key");
        }
        log.debug(">>>>>>>>>>>>>>>>>> adding fcm token to user group");
        List<String> registrationIdsNew = new ArrayList<String>();
        registrationIdsNew.add(token);
        String currentUserGroupKey = null;
        if(groupNotificationKey == null){
            currentUserGroupKey=  this.updateGroupToken(headers,userProfile.getId(),"create",groupNotificationKey,registrationIdsNew);
        }else{
            currentUserGroupKey =this.updateGroupToken(headers,userProfile.getId(),"add",groupNotificationKey,registrationIdsNew);
        }
        if(currentUserGroupKey == null){
            log.debug(">>>>>>>>>>>>>>>>>> null returned when added to group");
            return null;
        }
        userProfile.setFcmToken(currentUserGroupKey);
        userProfile  = userProfileRepository.save(userProfile);
        if(userProfile != null){
            return "success";
        }
        return null;
    }

    public String deleteToken(String token) {
        log.debug(">>>>>>>>>>>>>>>>>> request to delete fcm token for current user");
        if(token == null ){
            return null;
        }
        UserProfile userProfile = this.getCurrentUserProfile();
        if(userProfile==null){
            return null;
        }
        FcmToken fcmToken = fcmTokenRepository.findOneByToken(token);
        if(fcmToken==null){
            log.debug(">>>>>>>>>>>>>>>>>> token not found in db");
            return null;
        }
        if(fcmToken.getUserProfile().getId()!=userProfile.getId()){
            log.debug(">>>>>>>>>>>>>>>>>> token found for other user");
            return null;
        }
        HttpHeaders headers = this.makeHeaders(true);
        String notificationKey = this.getFcmGroupToken(headers,userProfile.getId());
        if(notificationKey==null){
            return null;
        }
        List<String> registrationIds = new ArrayList<String>();
        registrationIds.add(token);
        notificationKey = this.updateGroupToken(headers,userProfile.getId(),"remove",notificationKey,registrationIds);
        if(notificationKey==null) {
            log.debug(">>>>>>>>>>>>>>>>>> error while deleting token from group");
            return null;
        }
        try{
            this.fcmTokenRepository.delete(fcmToken);
            List<FcmToken> tokens = this.fcmTokenRepository.findAllForUser(userProfile.getId());
            if(tokens==null || (tokens!=null && tokens.isEmpty())){
                userProfile.setFcmToken(null);
                this.userProfileRepository.save(userProfile);
            }
        }catch (Exception e){
            log.debug(">>>>>>>>>>>>>>>>>> error while deleting token from db");
            return null;
        }
        return "success";
    }

}
