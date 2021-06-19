/**
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package conf;

import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;
import controllers.FeedControllerImpl;
import controllers.UsersControllerImpl;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  
        
        router.GET().route("/").with(ApplicationController.class, "index");
        
        //Users
        router.GET().route("/get/user/{userId}").with(UsersControllerImpl.class, "getUserById");
        router.POST().route("/create/user").with(UsersControllerImpl.class, "createUser");
        router.PUT().route("/update/user").with(UsersControllerImpl.class, "updateUser");
        router.DELETE().route("/delete/user/{userId}").with(UsersControllerImpl.class, "deleteUserById");
        
        //Feeds
        router.GET().route("/get/feed/{userId}/{feedId}").with(FeedControllerImpl.class, "getFeedById");
        router.POST().route("/create/feed").with(FeedControllerImpl.class, "createFeed");
        router.PUT().route("/update/feed/{userId}").with(FeedControllerImpl.class, "updateFeed");
        router.DELETE().route("/delete/feed/{userId}/{feedId}").with(FeedControllerImpl.class, "deleteFeedById");
        
        router.GET().route("/get/feeds").with(FeedControllerImpl.class, "getFeedsByUserId");
        
        router.GET().route("/*").with(ApplicationController.class, "index");
    }

}
