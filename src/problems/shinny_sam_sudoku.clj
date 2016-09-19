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



;------------------------test----------------------------
(def start-state (->State [       [0 0 0 2 6 0 7 0 1]
                                  [6 8 0 0 7 0 0 9 0]
                                  [1 9 0 0 0 4 5 0 0]

                                  [8 2 0 1 0 0 0 4 0]
                                  [0 0 4 6 0 2 9 0 0]
                                  [0 5 0 0 0 3 0 2 8]

                                  [0 0 9 3 0 0 0 7 4]
                                  [0 4 0 0 5 0 0 3 6]
                                  [7 0 3 0 1 8 0 0 0]] [3, 4]))
;------------------------test----------------------------


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

    (if (= y 8)
      [(+ x 1) (- y 8)]
      [x (+ y 1)])))



;divide into 3*3 and return a coll
(defn grid [board hpos vpos]
  (for [x[(* hpos 3) (+ (* hpos 3) 1) (+ (* hpos 3) 2)] y[(* vpos 3) (+ (* vpos 3) 1) (+ (* vpos 3) 2)]]
    (get-in board [x y])))


;verify if it is legal and return a coll of true or false
(defn grid-legal? [state]
  (for [x[0 1 2] y[0 1 2]]
    (legal? (grid (:board start-state) x y))))

;check if the whole board is legal
(defn board-legal? [state]

  (let       [row (row-seq (:board state) (first (:curr-position state)))
             col (col-seq (:board state) (second (:curr-position state)))]
          (and (legal? row) (legal? col) (every? true? (grid-legal? state)))
    ))



;------------------------test----------------------------
(add (:board start-state) (:curr-position start-state) 3)

(legal? (grid (add (:board start-state) (:curr-position start-state) 3) 1 1))

(->State (add (:board start-state) (:curr-position start-state) 3) [3 4])

(grid-legal? start-state)

(grid-legal? (->State (add (:board start-state) (:curr-position start-state) 3) [3 4]))

(board-legal? start-state)
;------------------------test----------------------------




;children function
(defn children [state]
  (let [x (first (:curr-position state))
        y (second (:curr-position state))]
    (for [num [1 2 3 4 5 6 7 8 9]
          :let [new-state (add (:board state) (:curr-position state) num)]
          :when (board-legal? (->State new-state [x y]))
          ]
      (if (not= 0 (get-in (:board start-state) [x y])) (->State (:board state) (updatePos state))
      (->State new-state (updatePos state))))))




;------------------------test----------------------------
(for [num [1]]
 (->State (add (:board  start-state)
               (:curr-position  start-state) num) (updatePos start-state)))

(board-legal? (->State (add (:board  start-state) (:curr-position  start-state) 3) [3 4]))
(children start-state)
;------------------------test----------------------------

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

