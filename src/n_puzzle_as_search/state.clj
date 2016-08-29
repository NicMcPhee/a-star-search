(ns n-puzzle-as-search.state)

(defrecord State [board blank-position])

(defn swap [board old-x old-y new-x new-y]
  (

(defn children [state]
  (let [x (first (:blank-position state))
        y (second (:blank-position state))]
  (for [dx [-1 1]
        dy [-1 1]
        :let [new-x (+ x dx)
              new-y (+ y dy)]
        :when (legal? new-x)
        :when (legal? new-y)]
    (->State (swap (:board state) x y new-x new-y)
             [new-x new-y]))))
