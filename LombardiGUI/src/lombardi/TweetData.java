package lombardi;

import java.util.Date;

public class TweetData {

    String userName;
    String toUser;
    String tweetText;
    String imageUrl;
    String imageUrlToUser;
    Date createDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String twetText) {
        this.tweetText = twetText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlToUser() {
        return imageUrlToUser;
    }

    public void setImageUrlToUser(String imageUrlToUser) {
        this.imageUrlToUser = imageUrlToUser;
    }
    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if(obj == null)
            return false;
        
        if(obj instanceof TweetData){
            return this.userName.equals(((TweetData)obj).userName);
        }
        
        return false;
        
    }
}
