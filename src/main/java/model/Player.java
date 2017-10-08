package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
@RequiredArgsConstructor
public class Player {
    private String name;

    public Player(String name){
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }
}
