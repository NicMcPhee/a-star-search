(ns problems.maze)

(defrecord State [board current-pos])

(defn children [state]
  (let [x (first (:current-pos state))
        y (second (:current-pos state))
        board-y (count (:board state))
        board-x (count (first (:board state)))]
  (for [[dx dy] [[-1, 0] [1, 0], [0, -1], [0, 1]]
        :let [new-x (+ x dx)
              new-y (+ y dy)]
        :when (legal? board-x board-y [new-x new-y] (:board state))]
    (->State (:board state) [new-x new-y]))))

(def x -10) ;Pay no attention to the x behind the curtain. This varibale lets us use x without quotes.

(defn legal? [board-width board-height new-pos board]

  (true? (>= (first new-pos) 0))
 ; (>= (second new-pos) 0)
 ; (< (first new-pos) board-width)
 ;(< (second new-pos) board-height)
 ; (> (get-in board new-pos) 0)

  (and (>= (first new-pos) 0)
       (>= (second new-pos) 0)
       (< (first new-pos) board-width)
       (< (second new-pos) board-height)
       (> (get-in board new-pos) 0)))

(+ x 1)

;Bits and pieces for sanity's sake


(def test-board [[0 0 0 0]
                 [x x x 0]
                 [0 x x 0]
                 [0 0 0 x]
                 [0 x 0 0]])

(children (->State test-board [0 1]))
