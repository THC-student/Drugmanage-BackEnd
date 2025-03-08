package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestBack {
   private String userName;
   private  String userHeader;
   private Integer userJudge;
   private String content;
   private String time;
   private String msgId;
}
