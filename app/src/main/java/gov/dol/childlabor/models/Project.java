package gov.dol.childlabor.models;

import java.io.Serializable;

public class Project implements Serializable {
        String title;
        String link;

        public Project(String title, String link) {
            this.title = title;
            this.link = link;
        }

        public Project() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }