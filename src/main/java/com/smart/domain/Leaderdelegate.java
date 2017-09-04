package com.smart.domain;

public class Leaderdelegate {
    private Leader leader;

    public Leaderdelegate(Leader leader) {
        this.leader = leader;
    }

    public void service() {
        leader.greet();
        leader.service();
    }

}
