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

(defn legal? [board-width board-height current-pos new-pos]
  (and (>= (:x current-pos) 0)
       (>= (:y current-pos) 0)
       (< (:x current-pos) board-width)
       (< (:y current-pos) board-height)))
