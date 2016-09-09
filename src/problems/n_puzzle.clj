(ns problems.n-puzzle)

(defrecord State [board blank-position])

(defn legal? [board-size n]
  (and (<= 0 n) (< n board-size)))

(defn swap [board old-pos new-pos]
  (let [old-value (get-in board old-pos)
        new-value (get-in board new-pos)]
    (assoc-in (assoc-in board old-pos new-value)
              new-pos old-value)))

(defn children [state]
  (let [x (first (:blank-position state))
        y (second (:blank-position state))
        board-size (count (:board state))]
  (for [[dx dy] [[-1, 0] [1, 0], [0, -1], [0, 1]]
        :let [new-x (+ x dx)
              new-y (+ y dy)]
        :when (legal? board-size new-x)
        :when (legal? board-size new-y)]
    (->State (swap (:board state) [x y] [new-x new-y])
             [new-x new-y]))))

(defn prefer-horizontal-cost [s t]
  (let [s-x (first (:blank-position s))
        t-x (first (:blank-position t))]
    (inc (Math/abs (- s-x t-x)))))
