(ns problems.shinny-sam-sudoku)

(defrecord State [board curr-position])

;check row/column for duplicates
(defn duplicates? [coll]
  (not (apply distinct? (filter #(> % 0) coll))))

;example to test all the other functions
(def start-state (->State [[0 0 0 2 6 0 7 0 1]
                           [6 8 0 0 7 0 0 9 0]
                           [1 9 0 0 0 4 5 0 0]

                           [8 2 0 1 0 0 0 4 0]
                           [0 0 4 6 0 2 9 0 0]
                           [0 5 0 0 0 3 0 2 8]

                           [0 0 9 3 0 0 0 7 4]
                           [0 4 0 0 5 0 0 3 6]
                           [7 0 3 0 1 8 0 0 0]] [8, 8]))

;check if a value/element is in a collection
(defn in? [coll value]
  (= true (some #(= value %) coll)))

;return a row
(defn row-seq [board y]
  (nth board y))

;return a column
(defn col-seq [board x]
  (for [y board]
    (last(take (+ x 1) y))))

;check if a row, column or 3x3 is legal
(defn legal? [coll]
  (and (not (duplicates? coll)) (= true (in? coll 0))))


;the range of number(1-9) to put into the board
(def numBank (range 1 10))

;put numbers into the current position of the board
(defn add [board position number numBank]
  (let [num (nth numBank (- number 1))]
    (assoc-in board position num)))


;update the current position of the board (don't know if I can call the function and return a new position somewhere else)
(defn updatePos [state]
  (let [x (first (:curr-position state))
        y (second (:curr-position state))]

    (if (= y 8)
      [(+ x 1) (- y 8)]
      [(+ x 0) (+ y 1)])))



;go through the number 1-9 to put in the current position and produce different childrens of States of the board(haven't implement the legal function yet)
(for [num [1 2 3 4 5 6 7 8 9]]
  (->State (add (:board  start-state)
                (:curr-position  start-state) num numBank) (updatePos start-state)))


;divide into 3*3 and return a coll
(defn grid-1 [board]
  (for [x[0 1 2] y[0 1 2]]
    (get (get board x) y)))

(defn grid-2 [board]
  (for [x[0 1 2] y[3 4 5]]
    (get (get board x) y)))

(defn grid-3 [board]
  (for [x[0 1 2] y[6 7 8]]
    (get (get board x) y)))

(defn grid-4 [board]
  (for [x[3 4 5] y[0 1 2]]
    (get (get board x) y)))

(defn grid-5 [board]
  (for [x[3 4 5] y[3 4 5]]
    (get (get board x) y)))

(defn grid-6 [board]
  (for [x[3 4 5] y[6 7 8]]
    (get (get board x) y)))

(defn grid-7 [board]
  (for [x[6 7 8] y[0 1 2]]
    (get (get board x) y)))

(defn grid-8 [board]
  (for [x[6 7 8] y[3 4 5]]
    (get (get board x) y)))

(defn grid-9 [board]
  (for [x[6 7 8] y[6 7 8]]
    (get (get board x) y)))


;children function
(defn children [state]
  (let [x (first (:curr-position state))
        y (second (:curr-position state))]

    (for [num [1 2 3 4 5 6 7 8 9]

          :let [row (row-seq (:board state) y)
                col (col-seq (:board state) x)]
          :when (legal? row)
          :when (legal? col)
          :when (legal? (grid-1(:board state)))
          :when (legal? (grid-2(:board state)))
          :when (legal? (grid-3(:board state)))
          :when (legal? (grid-4(:board state)))
          :when (legal? (grid-5(:board state)))
          :when (legal? (grid-6(:board state)))
          :when (legal? (grid-7(:board state)))
          :when (legal? (grid-8(:board state)))
          :when (legal? (grid-9(:board state)))]
      (->State (add (:board  state)
                    (:curr-position  state) num numBank) (updatePos state)))))



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










