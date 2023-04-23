package com.blogapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesModule {
    String name;
    String department;
    private Logger logger  = LoggerFactory.getLogger(SalesModule.class);

    void getSaleInformation(){
        logger.info("sale infor", this.name);
    }
}
