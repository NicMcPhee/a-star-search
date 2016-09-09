(ns problems.random-walk)

; A simple "puzzle" where you start at [0, 0]
; and need to get to [10, 10] by moving either left,
; right, up, or down.

(defrecord State [x y])

(defn children [state]
  (let [x (:x state)
        y (:y state)]
    (for [[dx dy] [[-1, 0] [1, 0], [0, -1], [0, 1]]
          :let [new-x (+ x dx)
                new-y (+ y dy)]]
      (->State new-x new-y))))
