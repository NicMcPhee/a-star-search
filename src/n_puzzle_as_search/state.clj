(ns n-puzzle-as-search.state)

(defrecord State [board blank-position])

; 0≤n<3
(defn legal? [n]
  (and (<= 0 n) (< n 3)))

(defn swap [board old-pos new-pos]
  (let [old-value (get-in board old-pos)
        new-value (get-in board new-pos)]
    (assoc-in (assoc-in board old-pos new-value)
              new-pos old-value)))

(defn children [state]
  (let [x (first (:blank-position state))
        y (second (:blank-position state))]
  (for [[dx dy] [[-1, 0] [1, 0], [0, -1], [0, 1]]
        :let [new-x (+ x dx)
              new-y (+ y dy)]
        :when (legal? new-x)
        :when (legal? new-y)]
    (->State (swap (:board state) [x y] [new-x new-y])
             [new-x new-y]))))
