(ns problems.richardsmitchellettesudoku)

(defrecord State [board new-value])


(defn getSection [board box] (nth board (int (/ box 3))))

(defn getBox [board box] (nth
                           (nth board (int (/ box 3)))
                         (rem box 3)))

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

(defn getByRowCol [board row col] (getByBox
                                    board
                                    (+ (* 3 (int (/ row 3))) (int (/ col 3)))
                                    (rem row 3)
                                    (rem col 3)))


(defn getBoxValues [board box] (into []
                                     (concat
                                       (nth (getBox board box) 0)
                                       (nth (getBox board box) 1)
                                       (nth (getBox board box) 2))))

(defn checkBox [board box new-value] (not=
                                       (some #{new-value} (getBoxValues board box))
                                     new-value))

(defn checkRow [board row col new-value] (if
                                           (= (getByRowCol board row col) new-value)
                                           false
                                           (if
                                             (< col 8)
                                             (checkRow board row (+ col 1) new-value)
                                             true)))

(defn checkCol [board row col new-value] (if
                                           (= (getByRowCol board row col) new-value)
                                           false
                                           (if
                                             (< row 8)
                                             (checkCol board (+ row 1) col new-value)
                                             true)))

(defn legal? [board row col new-value] (and
                                         (checkBox board (+ (* 3 (int (/ row 3))) (int (/ col 3))) new-value)
                                         (checkRow board row 0 new-value)
                                         (checkCol board 0 col new-value)))


(defn iterator [board row col] (if
                                 (= (getByRowCol board row col) 0)
                                 [row col]
                                 (if
                                   (< col 8)
                                   (iterator board row (+ col 1))
                                   (if (< row 8) (iterator board (+ row 1) 0)))))

(defn getToChange [board] (iterator board 0 0))


(defn changeValue-helper [board box boxRow boxCol new-value] (assoc board (int (/ box 3))
                                                               (assoc (getSection board box) (rem box 3)
                                                                 (assoc (getBox board box) boxRow
                                                                   (assoc (getBoxRow board box boxRow) boxCol new-value)))))

(defn changeValue [board row col new-value]
  (changeValue-helper board (+ (* 3 (int (/ row 3))) (int (/ col 3))) (rem row 3) (rem col 3) new-value))


(defn getBoxSansNum [board new-value]
  (for [incr (range 0 9)
        :let [box incr]
        :when (checkBox board box new-value)]
    box))

(defn eliminateRow [board box new-value]
  (for [incr (range 0 3)
       :let [boxRow incr]
       :when (checkRow board (+ (* (int (/ box 3)) 3) boxRow) 0 new-value)]
    boxRow))

(defn eliminateCol [board box new-value]
  (for [incr (range 0 3)
       :let [boxCol incr]
       :when (checkCol board 0 (+ (* (int (rem box 3)) 3) boxCol) new-value)]
    boxCol))

(defn combinations [board box new-value]
  (for [valid-rows (eliminateRow board box new-value)
        valid-cols (eliminateCol board box new-value)]
    [valid-rows valid-cols]))

(defn canSolve [board box new-value] (= 1
                                        (count
                                          (for [valid-spots (combinations board box new-value)
                                                :when (= 0 (getByBox board box (first valid-spots) (second valid-spots)))]
                                            valid-spots))))

(defn weighter [board new-value] (count
                                   (for [box (getBoxSansNum board new-value)
                                         :when (canSolve board box new-value)]
                                     1)))

(defn heuristic-helper [board new-value initial-weight] (loop
                                                          [weight (weighter board new-value)]
                                                          weight))

(defn openSpots [board] (count (filter zero? (flatten board))))

(defn heuristic [state] (max 0 (- (openSpots (:board state)) (heuristic-helper (:board state) (:new-value state) 0))))


(defn children [state]
(let [board (:board state)
      row (first (getToChange (:board state)))
      col (second (getToChange (:board state)))]
  (for [incr (range 1 10)
        :let [new-value incr]
        :when (legal? board row col new-value)]
    (->State (changeValue board row col new-value) new-value))))
