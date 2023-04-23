package com.blogapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Viewer {
    private String name;
    private int  id;

    public void getInformation(){
        System.out.println("get information about viver");
    }
}
