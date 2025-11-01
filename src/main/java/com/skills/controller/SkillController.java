package com.skills.controller;

import com.skills.entity.Skill;
import com.skills.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:3000") // Pour React
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Skill skill = skillService.createSkill(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(skill);
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkils());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        Optional<Skill> skill = skillService.getSkillById(id);
        return skill.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<Skill> updateProgress(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body) {
        Integer progress = body.get("progress");
        if (progress == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Skill> skill = skillService.updateProgress(id, progress);
        return skill.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        boolean deleted = skillService.deleteSkillById(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}