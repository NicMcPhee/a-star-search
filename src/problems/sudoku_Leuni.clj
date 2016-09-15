(ns problems.sudoku-Leuni)

;Our state is only the current board
(defrecord State [board current-pos])


(defn all-pos [state]
  (for [x (range 9)] (if (> (get (get (:board state) (first (:current-pos state))) (second (:current-pos state))) 0)
                       (State. (:board state) (if (= (first (:current-pos state)) 2) [0 (+ 1 (second (:current-pos state)))] [(+ 1 (first (:current-pos state))) (second (:current-pos state))] ))
                       (State. (assoc (:board state) (first (:current-pos state)) (assoc (get (:board state) (first (:current-pos state)))  (second (:current-pos state) ) (+ 1 x)))
                             (if (= (first (:current-pos state)) 2) [0 (+ 1 (second (:current-pos state)))] [(+ 1 (first (:current-pos state))) (second (:current-pos state))] )))))

(def temp
  (State. [[0 0 0] [3 2 0]] [1 0]))

(def temp2
  (State. [[1 1 1] [1 1 1]] [1 1]))

(def state-map
  [temp temp2])

(all-pos temp2)

(defn check-row [state]
  (= (.indexOf (get (:board state) (first (:current-pos state))) (get (get (:board state) (first (:current-pos state))) (second (:current-pos state))))
     (.lastIndexOf (get (:board state) (first (:current-pos state))) (get (get (:board state) (first (:current-pos state))) (second (:current-pos state))))))

(check-row temp)

(defn check-column [state]

)

(defn check-square [state]

)

(defn isLegal? [state old-pos]
  (and (check-row state) (check-column state) (check-square state)))

(defn children [state]
  (let [old-pos (:current-pos state)]
    (filter #(isLegal? % old-pos) (all-pos state))
        )
  )
