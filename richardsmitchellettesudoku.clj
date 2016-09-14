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
(defn checkBox [board box new-value] (not= (some #{new-value} (getBoxValues board box)) new-value))
(defn checkRow [board row col new-value] (if (= (getByRowCol board row col) new-value) false (if (< col 8) (checkRow board row (+ col 1) new-value) true)))
(defn checkCol [board row col new-value] (if (= (getByRowCol board row col) new-value) false (if (< row 8) (checkCol board (+ row 1) col new-value) true)))

(defn legal? [board row col new-value] (and (checkBox board (+ (* 3 (int (/ row 3))) (int (/ col 3))) new-value) (checkRow board row 0 new-value) (checkCol board 0 col new-value)))
