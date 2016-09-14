(ns problems.richardsmitchellettesudoku)

(defrecord State [board])

(defn getSection [board box] (nth board (int (/ box 3))))
(defn getBox [board box] (nth (nth board (int (/ box 3))) (rem box 3)))

(defn getBoxRow [board box boxRow] (nth
                               (nth
                                 (nth board (int (/ box 3)))
                                    (rem box 3))
                                      boxRow))

(defn getByBox [board box boxRow boxCol] (nth
                                           (nth
                                             (nth
                                               (nth board (int (/ box 3)))
                                             (rem box 3))
                                           boxRow)
                                         boxCol))
(defn getByRowCol [board row col] (getByBox board (+ (* 3 (int (/ row 3))) (int (/ col 3))) (rem row 3) (rem col 3)))


(defn getBoxValues [board box] (into [] (concat (nth (getBox board box) 0) (nth (getBox board box) 1) (nth (getBox board box) 2))))