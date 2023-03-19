package com.example.testing;

public class MODELCLASS {
        String key1, key2, imagekey;

        public MODELCLASS() {
        }

        public MODELCLASS(String key1, String key2, String imagekey) {
            this.key1 = key1;
            this.key2 = key2;
            this.imagekey = imagekey;
        }

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public String getKey2() {
            return key2;
        }

        public void setKey2(String key2) {
            this.key2 = key2;
        }

        public String getImagekey() {
            return imagekey;
        }

        public void setImagekey(String imagekey) {
            this.imagekey = imagekey;
        }


    }
