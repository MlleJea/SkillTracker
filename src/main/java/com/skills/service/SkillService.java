package com.skills.service;

import com.skills.entity.Skill;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SkillService {

    private final List<Skill> skills = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Skill createSkill(String name) {
        Long id = idCounter.getAndIncrement();
        Skill skill = new Skill(id,name, 0);
        skills.add(skill);
        return skill;
    }

    public List<Skill> getAllSkils(){
        return new ArrayList<>(skills);
    }

    public Optional<Skill> getSkillById(Long id){
        return skills.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public Optional<Skill> updateProgress(Long id, int progress){
        Optional<Skill> skillOpt = getSkillById(id);
        if(skillOpt.stream().findAny().isPresent()) {
            Skill skill = skillOpt.get();
            skill.setProgress(progress);
            return Optional.of(skill);
        }
        return Optional.empty();
    }

    public boolean deleteSkillById(Long id){
        return skills.removeIf(s -> s.getId().equals(id));
    }

}

