package com.cjh.common.boss.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SyncFriendStatus {

    /**
     * resumeAuthStatus
     */
    private Integer resumeAuthStatus;
    /**
     * contactAuthStatus
     */
    private Integer contactAuthStatus;

}
