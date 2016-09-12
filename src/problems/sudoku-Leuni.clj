(ns problems.sudoku-Leuni)

;Our state is only the current board
(defrecord State [board current-pos])

(defn isMultiple [vec num]
  (if (= (.indexOf vec num) (.lastIndexOf vec num)) false true))

;(defn legal? [State]
;  (map isMultiple (first (:current-pos State))


(defn children [state]
