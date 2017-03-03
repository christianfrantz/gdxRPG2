package com.gdx.rpg.Observer;



import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Quests.Quest;

import java.util.LinkedList;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class Subject {
        public LinkedList<Observer> observers = new LinkedList<Observer>();
        public int numOfObservers;

        public void AddObserver(Observer ob) {
            if(!observers.contains(ob)){
                observers.add(ob);
                numOfObservers++;
            }
        }

        public void RemoveObserver(Observer ob){
            if(observers.contains(ob)){
                observers.remove(ob);
                numOfObservers--;
            }
        }

        public void notify(Player player, Event event){
            for(int i = 0; i < numOfObservers; i++){
                observers.get(i).onNotify(player, event);
            }
        }

        public void notify(Player player, Entity enemy, Event event){
            for(int i = 0; i < numOfObservers; i++){
                observers.get(i).onNotify(player, enemy, event);
            }
        }

        public void notify(Quest quest, Event event){
            for(int i = 0; i < numOfObservers; i++){
                observers.get(i).onNotify(quest, event);
            }
        }

}
