export function ScoreBoard({ scores, currentPlayer }) {
  return (
    <section className="score-board" aria-label="Marcador">
      <div className={`score-card ${currentPlayer === 'X' ? 'score-card--active' : ''}`}>
        <span className="score-symbol score-symbol--x">X</span>
        <span>Jugador X</span>
        <strong>{scores.X}</strong>
      </div>
      <div className="score-card score-card--draw">
        <span>Empates</span>
        <strong>{scores.draws}</strong>
      </div>
      <div className={`score-card ${currentPlayer === 'O' ? 'score-card--active' : ''}`}>
        <span className="score-symbol score-symbol--o">O</span>
        <span>Jugador O</span>
        <strong>{scores.O}</strong>
      </div>
    </section>
  )
}
