import { useState } from 'react'
import { Square } from './components/Square.jsx'
import { ScoreBoard } from './components/ScoreBoard.jsx'

const WINNING_LINES = [
  [0, 1, 2], [3, 4, 5], [6, 7, 8],
  [0, 3, 6], [1, 4, 7], [2, 5, 8],
  [0, 4, 8], [2, 4, 6],
]

function getWinner(board) {
  for (const line of WINNING_LINES) {
    const [a, b, c] = line
    if (board[a] && board[a] === board[b] && board[a] === board[c]) {
      return { player: board[a], line }
    }
  }
  return null
}

const emptyBoard = () => Array(9).fill(null)

export default function App() {
  const [board, setBoard] = useState(emptyBoard)
  const [isXTurn, setIsXTurn] = useState(true)
  const [scores, setScores] = useState({ X: 0, O: 0, draws: 0 })

  const winner = getWinner(board)
  const isDraw = !winner && board.every(Boolean)
  const currentPlayer = isXTurn ? 'X' : 'O'

  function playSquare(index) {
    if (board[index] || winner || isDraw) return

    const nextBoard = [...board]
    nextBoard[index] = currentPlayer
    const roundWinner = getWinner(nextBoard)
    const roundDraw = !roundWinner && nextBoard.every(Boolean)

    setBoard(nextBoard)

    if (roundWinner) {
      setScores((previous) => ({ ...previous, [roundWinner.player]: previous[roundWinner.player] + 1 }))
    } else if (roundDraw) {
      setScores((previous) => ({ ...previous, draws: previous.draws + 1 }))
    } else {
      setIsXTurn((previous) => !previous)
    }
  }

  function startNewRound() {
    setBoard(emptyBoard())
    setIsXTurn(true)
  }

  function resetGame() {
    startNewRound()
    setScores({ X: 0, O: 0, draws: 0 })
  }

  const status = winner
    ? `¡Ganó el jugador ${winner.player}!`
    : isDraw
      ? 'La ronda terminó en empate.'
      : `Turno del jugador ${currentPlayer}`

  return (
    <main className="app-shell">
      <section className="game-panel">
        <header>
          <p className="eyebrow">Ejercicio 2c.2 · Introducción a React</p>
          <h1>Tres en línea</h1>
          <p className="subtitle">Completá una fila de tres símbolos para ganar la ronda.</p>
        </header>

        <ScoreBoard scores={scores} currentPlayer={winner || isDraw ? null : currentPlayer} />

        <p className={`game-status ${winner ? 'game-status--winner' : ''}`} role="status">
          {status}
        </p>

        <div className="board" role="grid" aria-label="Tablero de tres en línea">
          {board.map((value, index) => (
            <Square
              key={index}
              value={value}
              isWinning={winner?.line.includes(index)}
              onSquareClick={() => playSquare(index)}
            />
          ))}
        </div>

        <div className="actions">
          <button className="button button--primary" onClick={startNewRound}>Nueva ronda</button>
          <button className="button button--secondary" onClick={resetGame}>Reiniciar marcador</button>
        </div>
      </section>
    </main>
  )
}
