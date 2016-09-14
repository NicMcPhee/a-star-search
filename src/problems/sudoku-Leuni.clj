(ns problems.sudoku-Leuni)

;Our state is only the current board
(defrecord State [board current-pos])


(defn all-pos [state]
  (for [x (range 9)] (assoc (:board state) (first (:current-pos state)) (assoc (get (:board state) (first (:current-pos state)))  (second (:current-pos state) ) (+ 1 x)))))

(def temp
  (State. [[0 0 0] [0 0 0]] [0 0]))

(def temp2
  (State. [[1 1 1] [1 1 1]] [1 1]))

(def state-map
  [temp temp2])

(map all-pos state-map)

(defn check-row [state]

)

(defn check-column [state]

)

(defn check-square [state]

)

(defn isLegal? [state]
  (and (check-row state) (check-column state) (check-square state)))

(defn children [state]

