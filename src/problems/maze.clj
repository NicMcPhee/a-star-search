(ns problems.maze)

(defrecord State [board current-pos])

(def . ".") ;Pay no attention to the x behind the curtain. This varibale lets us use x without quotes.

(defn legal? [new-pos board]
  (let [board-rows (count board)
        board-cols (count (first board))]

  (and (>= (first new-pos) 0)
       (>= (second new-pos) 0)
       (< (first new-pos) board-rows)
       (< (second new-pos) board-cols)
       (not (= (get-in board new-pos) ".")))))

(defn heuristic [goal-state new-state]
  (let [x1 (first (:current-pos goal-state))
        y1 (second (:current-pos goal-state))
        x2 (first (:current-pos new-state))
        y2 (second (:current-pos new-state))
        dx (- x1 x2)
        dy (- y1 y2)]
    (* dy dx)))

(defn get-cost [curr-state new-state]
  (get-in (:board new-state) (:current-pos new-state)))

(defn children [state]
  (let [row (first (:current-pos state))
        col (second (:current-pos state))]
    (for [[dr dc] [[-1 0] [1 0] [0 -1] [0 1]]
      :let [new-row (+ row dr)
            new-col (+ col dc)]
      :when (legal? [new-row new-col] (:board state))]
      (->State (:board state) [new-row new-col]))))
     ; (->State (assoc-in (:board state) [row col] "_") [new-row new-col]))))


(def test-board [[0 0 0 0 0 . 0 0 0 . . . 0 . . 0]
                 [0 0 0 0 0 . 0 0 0 . . . 0 . . 0]
                 [. . . . 0 . . . 0 . . . 0 0 0 0]
                 [0 . . . 0 . 0 0 0 0 0 . 0 . 0 .]
                 [0 0 0 . 0 . 0 . . . 0 0 0 . 0 .]
                 [0 0 0 . 0 0 0 . . . . . . . . .]
                 [0 0 0 0 . . 0 0 0 . . . 0 0 0 .]
                 [0 . . 0 0 . . . 0 0 0 0 0 . 0 .]
                 [0 . . . 0 . 0 0 0 . 0 . 0 . 0 .]
                 [0 0 0 . 0 . 0 . 0 0 0 . 0 . 0 0]
                 [0 . 0 . . . 0 . . . . . 0 . . 4]
                 [0 0 0 0 0 0 0 . . . . . . . 0 0]
                 [0 . . . 0 . . . 0 0 0 . 0 . 0 .]
                 [0 . . . 0 . 0 0 0 . 0 0 0 . 0 .]
                 [0 0 0 . 0 . 0 . 0 0 0 . 0 1 0 .]
                 [0 0 0 . 0 0 0 . . . . . 0 . 0 5]])

(children (->State test-board [0 0]))
