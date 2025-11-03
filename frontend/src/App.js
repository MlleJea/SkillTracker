import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const API_URL = 'http://localhost:8080/api/skills';

function App() {
  const [skills, setSkills] = useState([]);
  const [newSkillName, setNewSkillName] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // Charger les skills au démarrage
  useEffect(() => {
    fetchSkills();
  }, []);

  // Récupérer tous les skills
  const fetchSkills = async () => {
    try {
      setLoading(true);
      const response = await axios.get(API_URL);
      setSkills(response.data);
      setError('');
    } catch (err) {
      setError('Erreur lors du chargement des skills');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // Créer un nouveau skill
  const createSkill = async (e) => {
    e.preventDefault();
    if (!newSkillName.trim()) return;

    try {
      await axios.post(API_URL, { name: newSkillName });
      setNewSkillName('');
      fetchSkills();
    } catch (err) {
      setError('Erreur lors de la création de la skill');
      console.error(err);
    }
  };

  // Mettre à jour le progrès
  const updateProgress = async (id, currentProgress) => {
    const newProgress = Math.min(currentProgress + 10, 100);
    try {
      await axios.put(`${API_URL}/${id}/progress`, { progress: newProgress });
      fetchSkills();
    } catch (err) {
      setError('Erreur lors de la mise à jour');
      console.error(err);
    }
  };

  // Supprimer un skill
  const deleteSkill = async (id) => {
    if (!window.confirm('Supprimer cette skill ?')) return;

    try {
      await axios.delete(`${API_URL}/${id}`);
      fetchSkills();
    } catch (err) {
      setError('Erreur lors de la suppression');
      console.error(err);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Skill Progress Tracker</h1>
        <p>Suivez votre apprentissage de nouvelles compétences</p>
      </header>

      {error && <div className="error">{error}</div>}

      <div className="form-container">
        <form onSubmit={createSkill}>
          <input
            type="text"
            placeholder="Nouvelle compétence (ex: Karate, React...)"
            value={newSkillName}
            onChange={(e) => setNewSkillName(e.target.value)}
            className="skill-input"
          />
          <button type="submit" className="btn-add">
             Ajouter
          </button>
        </form>
      </div>

      <div className="skills-container">
        {loading ? (
          <p>Chargement...</p>
        ) : skills.length === 0 ? (
          <p className="empty-state">
            Aucune compétence pour le moment. Ajoutez-en une !
          </p>
        ) : (
          skills.map((skill) => (
            <div key={skill.id} className="skill-card">
              <div className="skill-header">
                <h3>{skill.name}</h3>
                <span className="progress-text">{skill.progress}%</span>
              </div>

              <div className="progress-bar-container">
                <div
                  className="progress-bar"
                  style={{ width: `${skill.progress}%` }}
                ></div>
              </div>

              <div className="skill-actions">
                <button
                  onClick={() => updateProgress(skill.id, skill.progress)}
                  className="btn-progress"
                  disabled={skill.progress >= 100}
                >
                  +10%
                </button>
                <button
                  onClick={() => deleteSkill(skill.id)}
                  className="btn-delete"
                >
                  Supprimer
                </button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default App;