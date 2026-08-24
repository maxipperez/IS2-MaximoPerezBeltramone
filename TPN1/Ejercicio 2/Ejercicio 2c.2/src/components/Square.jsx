export function Square({ value, onSquareClick, isWinning }) {
  return (
    <button
      className={`square square--${value?.toLowerCase() || 'empty'} ${isWinning ? 'square--winning' : ''}`}
      onClick={onSquareClick}
      aria-label={value ? `Casillero ocupado por ${value}` : 'Casillero vacío'}
    >
      {value}
    </button>
  )
}
