//package com.example.whatsappbot.client;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@SpringBootApplication
//public class App {
//    public static void main(String[] args) {
//        ConfigurableApplicationContext ctx = null;
//        try {
//            ctx = SpringApplication.run(App.class, args);
//            WebClient client = WebClient.create("https://graph.facebook.com");
//
//            WebClient.RequestHeadersSpec<?> headersSpec = client.post()
//                    .uri(uriBuilder -> uriBuilder
//                            .path("/v16.0/102199822777733/messages")
//                            .build())
//                    //.bodyValue("{'messaging_product':'whatsapp','to':'5534996991488','type':'text','text':{'preview_url':'false','body':'Ahoj'}}")
//                    .bodyValue("{\n" +
//                            "    \"messaging_product\": \"whatsapp\",\n" +
////                            "    \"to\": \"5534996991488\",\n" +
//                            "    \"to\": \"421918336295\",\n" +
////                            "    \"to\": \"5534999921488\",\n" +
//                            "    \"type\": \"text\",\n" +
//                            "    \"text\": {\n" +
//                            "        \"preview_url\": \"false\",\n" +
//                            "        \"body\": \"Ahoj Krasavica, ako sa mas? Estou esrevendo do meu Java aplicativo ;)\"\n" +
//                            "    }\n" +
//                            "}")
//                    .header("Authorization", "Bearer EAABh27OagwkBO8IwgAzOImC6vy4nJgRLZCt51CJHyRo3x3UsWKloZBfRZA800ZCEg2pK2abSczw8fMR48BIA5vThLetdeGk2ZB3T9UpocQZA6S8zkCbuz95ZA8yqRhehsZAoo40EgXWM2lqLZAZCBvm5ub4DD9mD4zwKkojJZA9I7c9ZAfKJO8XuMnZAZAPT4Icgfm4IMeK3ZCsmVXJl3zctAAYSVYAuvxvU0Nh6moNTYsZD")
//                    .header("Content-Type", "application/json");
//
//            WebClient.ResponseSpec responseSpec = headersSpec.retrieve();
//
//            Mono<String> response1 = headersSpec.exchangeToMono(response -> {
//                if (response.statusCode().equals(HttpStatus.OK)) {
//                    return response.bodyToMono(String.class);
//                } else if (response.statusCode().is4xxClientError()) {
//                    return response.bodyToMono(String.class);
//                } else {
//                    return response.createException()
//                            .flatMap(Mono::error);
//                }
//            });
//
//            System.out.println(response1.block());
////            System.out.println(responseSpec.bodyToMono(String.class).block());
//            ctx.close();
//        } catch (Throwable t) {
//            System.out.println(t);
//            ctx.close();
//        }
//    }
//}
