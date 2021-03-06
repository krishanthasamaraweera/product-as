/*
 * Copyright 2011-2012 WSO2, Inc. (http://wso2.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.hw.client;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import demo.hw.server.HelloWorld;
import demo.hw.server.User;
import demo.hw.server.UserImpl;

public final class Client {

    private static final QName SERVICE_NAME 
        = new QName("http://server.hw.demo/", "HelloWorld");
    private static final QName PORT_NAME 
        = new QName("http://server.hw.demo/", "HelloWorldPort");


    private Client() {
    } 

    public static void main(String args[]) throws Exception {
        Service service = Service.create(SERVICE_NAME);

        // Get Endpoint Address from an argument.
        // default : http://localhost:9763/java_first_jaxws-2.5.2/services/hello_world
        String endpointAddress = "http://localhost:9763/java_first_jaxws-2.5.2/services/hello_world";

        if (args[0] != null) {
            endpointAddress = args[0];
        }

        // Add a port to the Service
        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
        
        HelloWorld hw = service.getPort(HelloWorld.class);
        System.out.println(hw.sayHi("World"));

        User user = new UserImpl("World");
        System.out.println(hw.sayHiToUser(user));

        //say hi to some more users to fill up the map a bit
        user = new UserImpl("Galaxy");
        System.out.println(hw.sayHiToUser(user));

        user = new UserImpl("Universe");
        System.out.println(hw.sayHiToUser(user));

        System.out.println();
        System.out.println("Users: ");
        Map<Integer, User> users = hw.getUsers();
        for (Map.Entry<Integer, User> e : users.entrySet()) {
            System.out.println("  " + e.getKey() + ": " + e.getValue().getName());
        }

    }

}
