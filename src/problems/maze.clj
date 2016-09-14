(ns problems.maze)

(defrecord Coord [x y])
(defrecord State [board current-pos])

(defn children [state]

  )

(defn swap [board old-pos new-pos]
  (let [old-value (get-in board old-pos)
        new-value (get-in board new-pos)]
    (assoc-in (assoc-in board old-pos new-value)
              new-pos old-value)))
