package org.acme;

public class DataInfo {

    public DataInfo() {
    }

    public static DataInfo of(String cmUser, String cmPwd, String scUser, String scPwd) {
        var output = new DataInfo();
        output.configMapData.username = cmUser;
        output.configMapData.password = cmPwd;
        output.secretData.username = scUser;
        output.secretData.password = scPwd;
        return output;
    }

    private SecretData secretData = new SecretData();
    private ConfigMapData configMapData = new ConfigMapData();

    public SecretData getSecretData() {
        return secretData;
    }

    public void setSecretData(final SecretData secretData) {
        this.secretData = secretData;
    }

    public ConfigMapData getConfigMapData() {
        return configMapData;
    }

    public void setConfigMapData(final ConfigMapData configMapData) {
        this.configMapData = configMapData;
    }

    public static class SecretData {

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(final String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }

    }

    public static class ConfigMapData {

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(final String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }
    }

}
