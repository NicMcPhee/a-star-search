(ns problems.shinny-sam-sudoku)

(defrecord State [board curr-position])

;check row/column for duplicates
(defn duplicates? [coll]
  (not (apply distinct? (filter #(> % 0) coll))))


;return a row
(defn row-seq [board y]
  (nth board y))

;return a column
(defn col-seq [board x]
  (nth (apply map vector  board) x))

;check if a row, column or 3x3 is legal
(defn legal? [coll]
  (and (not (duplicates? coll))))


;put numbers into the current position of the board
(defn add [board position number]
    (assoc-in board position number))


;update the current position of the board
(defn updatePos [state]
  (let [x (first (:curr-position state))
        y (second (:curr-position state))]
    (if (= 0 (compare (:curr-position state) [8, 8]))
      [8, 8]
      (if (= y 8)
      [(+ x 1) (- y 8)]
      [x (+ y 1)]))))

;divide into 3*3 and return a coll
(defn grid [board hpos vpos]
  (for [x[(* hpos 3) (+ (* hpos 3) 1) (+ (* hpos 3) 2)] y[(* vpos 3) (+ (* vpos 3) 1) (+ (* vpos 3) 2)]]
    (get-in board [x y])))


;verify if it is legal and return a coll of true or false
(defn grid-legal? [state]
  (for [x[0 1 2] y[0 1 2]]
    (legal? (grid (:board state) x y))))

;check if the whole board is legal
(defn board-legal? [state]
  (let [row (row-seq (:board state) (first (:curr-position state)))
        col (col-seq (:board state) (second (:curr-position state)))]
            (and (legal? row) (legal? col) (every? true? (grid-legal? state)))))

;children function
(defn children [state]
  (let [x (first (:curr-position state))
        y (second (:curr-position state))]
    (for [num [1 2 3 4 5 6 7 8 9]
          :let [new-state (add (:board state) (:curr-position state) num)]
          :when (board-legal? (->State new-state [x y]))]
      (if (not= 0 (get-in (:board state) [x y])) (->State (:board state) (updatePos state))
      (->State new-state (updatePos state))))))


;heuristic helper function
(defn avg-diff [goal-state current-state]
  (- (/ (reduce + (flatten (:board goal-state)))
                     (count (filter #(> % 0)(flatten (:board goal-state)))))
               (/ (reduce + (flatten (:board current-state)))
                     (count (filter #(> % 0)(flatten (:board current-state)))))))

;heuristic function
(defn avg-difference [goal-state current-state]
  (if (= 0 (compare (:curr-position current-state) [8, 8]))
       0
      (if (= (avg-diff goal-state current-state) 0)
       1
          (* (avg-diff goal-state current-state) 3))))


;visual representation of a current sudoku board example's axis (currently operates in [y x])

;    x0 x1 x2 x3 x4 x5 x6 x7 x8
;y0  [0  1  2  0  0  0  0  0  0]
;y1  [3  4  5  0  0  0  0  0  0]
;y2  [6  7  8  0  0  0  0  0  0]

;y3  [0  1  2  0  0  0  0  0  0]
;y4  [3  4  5  0  0  0  0  0  0]
;y5  [6  7  8  0  0  0  0  0  0]

;y6  [0  1  2  0  0  0  0  0  0]
;y7  [3  4  5  0  0  0  0  0  0]
;y8  [6  7  8  0  0  0  0  0  0]

