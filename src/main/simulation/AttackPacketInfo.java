package simulation;

import network.AutonomousSystem;

import java.util.Stack;

class AttackPacketInfo {
    AutonomousSystem attacker;
    Stack<Integer> attackPath;

    public AttackPacketInfo(AutonomousSystem attacker, Stack<Integer> attackPath) {
        this.attacker = attacker;
        this.attackPath = attackPath;
    }
}
