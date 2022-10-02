package top.reminisce.coolnetblogcore.pojo.ao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDetail {
    private String email;
    private String emailPassword;
    private String smtpHost;
    private int smtpPort;
    private boolean smtpIsUseSsl;
}
