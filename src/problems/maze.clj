(ns problems.maze)

(defrecord Coord [x y])
(defrecord State [board current-pos])

(defn children [state]
  (let [x (first (:current-pos state))
        y (second (:current-pos state))
        board-x (count (:board state))
        board-y (count (first (:board state)))]
  (for [[dx dy] [[-1, 0] [1, 0], [0, -1], [0, 1]]
        :let [new-x (+ x dx)
              new-y (+ y dy)]
        :when (legal? board-x board-y [new-x new-y])]
    (->State (swap (:board state) [x y] [new-x new-y])
             [new-x new-y]))))

(def x "x") ;Pay no attention to the x behind the curtain. This varibale lets us use x without quotes.

(defn swap [board old-pos new-pos]
  (let [old-value (get-in board old-pos)
        new-value (get-in board new-pos)]
    (assoc-in (assoc-in board old-pos new-value)
              new-pos old-value)))

(defn legal? [board-width board-height new-pos]
  (and (>= (:x new-pos) 0)
       (>= (:y new-pos) 0)
       (< (:x new-pos) board-width)
       (< (:y new-pos) board-height)))

;Bits and pieces for sanity's sake

(:x (->Coord 1 2))
(:y (->Coord 1 2))

(:x (:current-pos (->State [[1 2] [1 2]] (->Coord 1 2))))


(count (first [[0 0 0 0]
               [0 x x 0]
               [0 x x 0]
               [0 0 0 x]
               [0 x 0 0]]))
