(ns problems.maze)

(defrecord State [board current-pos])

(def x "x") ;Pay no attention to the x behind the curtain. This varibale lets us use x without quotes.

(defn legal? [new-pos board]
  (let [board-rows (count board)
        board-cols (count (first board))]
  ;(println (and (>= (first new-pos) 0) (> (get-in board new-pos) 0)))
  ;(println (> (get-in board new-pos) 0))

  (and (>= (first new-pos) 0)
       (>= (second new-pos) 0)
       (< (first new-pos) board-rows)
       (< (second new-pos) board-cols)
       (not (= (get-in board new-pos) "x")))))

;Bits and pieces for sanity's sake

(defn children [state]
  (let [row (first (:current-pos state))
        col (second (:current-pos state))]
    (for [[dr dc] [[-1 0] [1 0] [0 -1] [0 1]]
      :let [new-row (+ row dr)
            new-col (+ col dc)]
      :when (legal? [new-row new-col] (:board state))]
      (->State (:board state) [new-row new-col]))))
     ; (->State (assoc-in (:board state) [row col] "_") [new-row new-col]))))


(def test-board [[0 0 0 0 0 x 0 0]
                 [x x x x 0 x x x]
                 [0 x x x 0 x 0 0]
                 [0 0 0 x 0 x 0 x]
                 [0 x 0 x 0 0 0 x]])

(children (->State test-board [0 0]))
