package com.ladtor.workflow.core.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/2/8 21:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    private String statusTimestamp;
    private String name;
    private String status;
    private int statusCode;
    private List<Instance> instances;

    @Data
    @NoArgsConstructor
    public static class Instance {
        private StatusInfo statusInfo;
        private String statusTimestamp;
        private boolean registered;
        private Registration registration;
        private String id;
        private int version;
        private List<Endpoint> endpoints;
    }

    @Data
    @NoArgsConstructor
    public static class Registration {
        private Metadata metadata;
        private String name;
        private String serviceUrl;
        private String managementUrl;
        private String source;
        private String healthUrl;

        @Data
        @NoArgsConstructor
        public static class Metadata {
            private String startup;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Endpoint {
        private String id;
        private String url;
    }

    @Data
    @NoArgsConstructor
    public static class StatusInfo {
        private String status;
    }
}
