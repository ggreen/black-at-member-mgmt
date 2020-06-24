package io.vmware.pod.black.blackatemailregistrationscrapper.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Gregory Green
 */
@Data
@Builder
public class BlackAtMember
{
    private Integer id;
    private String email;
    private String name;
    private String location;
    private String demographic;
    private String way;
    private LocalDateTime startTime;
    private LocalDateTime completion;
}
