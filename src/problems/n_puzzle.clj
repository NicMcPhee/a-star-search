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


;---------------------example-----------------------------------------
(def state-a (->State [[0 1 2] [3 4 5] [6 7 8]] [0 0]))
(children state-a)
(def start (->State [[1 0 2] [4 3 5] [6 7 8]] [0 1]))
;---------------------example-----------------------------------------



(defn prefer-horizontal-cost [s t]
  (let [s-x (first (:blank-position s))
        t-x (first (:blank-position t))]
    (inc (Math/abs (- s-x t-x)))))

(defn state->vec [state]
  (flatten (:board state)))

(defn num-wrong [goal-state current-state]
  (count (filter identity
                 (map not=
                      (state->vec goal-state)
                      (state->vec current-state)))))

(defn zero-or-same? [[x y]]
  (or (= x y)
      (zero? x)
      (zero? y)))

(defn num-non-blank-wrong [goal-state current-state]
  (count (remove zero-or-same?
                 (map (fn [x y] [x y])
                      (state->vec goal-state)
                      (state->vec current-state)))))
