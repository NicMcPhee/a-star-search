(ns problems.sudoku)


(defrecord State [board curr-position])


;check row/column for duplicates
(defn duplicates? [coll]
       (not (apply distinct? (filter #(> % 0) coll))))


;check if a value/element is in a collection
(defn in? [coll value]
  (= true (some #(= value %) coll)))


;check if a row, column or 3x3 is legal
(defn legal? [coll]
  (and (not (duplicates? coll)) (= false (in? coll 0))))


;test sequences

(def rows2 [0 0 3 3 5 6 7 8 9])

(def rows3 [3 1 3 4 5 9 7 8 6])

(def rows4 [3 1 2 4 5 9 7 8 6])


(not (duplicates? rows2))

(not (duplicates? rows3))

(not (duplicates? rows3))


(in? rows2 0)

(in? rows3 0)

(in? rows4 0)


(legal? rows2)

(legal? rows3)

(legal? rows4)


;generate viable sudoku input values
(def numBank (range 1 10))

;add to board function
(defn add [board position number numBank]
  (let [num (nth numBank (- number 1))]
    (assoc-in board position num)))



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


;test add functionality
(add [[0 1 2 0 0 0 0 0 0]
      [3 4 5 0 0 0 0 0 0]
      [6 7 8 0 0 0 0 0 0]

      [0 1 2 0 0 0 0 0 0]
      [3 4 5 0 0 0 0 0 0]
      [6 7 8 0 0 0 0 0 0]

      [0 1 2 0 0 0 0 0 0]
      [3 4 5 0 0 0 0 0 0]
      [6 7 8 0 0 0 0 0 0]] [1 0] 4 numBank)

;create an example start state to work with, will remove in final version
(def start-state (->State [[0 0 0 2 6 0 7 0 1]
                           [6 8 0 0 7 0 0 9 0]
                           [1 9 0 0 0 4 5 0 0]

                           [8 2 0 1 0 0 0 4 0]
                           [0 0 4 6 0 2 9 0 0]
                           [0 5 0 0 0 3 0 2 8]

                           [0 0 9 3 0 0 0 7 4]
                           [0 4 0 0 5 0 0 3 6]
                           [7 0 3 0 1 8 0 0 0]] [0, 0]))



;go through the number 1-9 to put in the current position and produce different childrens of States of the board(haven't implement the legal function yet)

(for [num [1 2 3 4 5 6 7 8 9]]
      (->State (add (:board  start-state)
     (:curr-position  start-state) num numBank) [0 0]))


;--------------in progress code/functions-------------------------------


;return sequence of the values in the row

(defn row-seq [board y]
  (nth board y))

;test function

(row-seq (:board start-state) 3)


;return sequence of the values in the column, maybe convert to a vector to match other function

(defn col-seq [board x y]
  (for [y board]
 (last(take x y))))


;test function
(col-seq (:board start-state) 2 2)

;add  sequence of the values in the column, in progress
;(defn 3x3 [board y x]
;  ())


;children function (in progress)


 (defn children [state]
  (let [y (first (:curr-position state))
        x (second (:curr-position state))]

        ;"if" statements aren't compiling, need to fix format

        ;check if at end of board, if so new x and new y won't matter

          (if (and (= y 8) (= x 8))
           "Done!"

          (let [new-x 0 new-y 0]))

        ;else if
        ;check if at end of row, if so move down to next one

          (if (= x 8)
          "end of line"

          (let [new-x (0)
                new-y (+ y 1)]))

        ;else
        ;otherwise continue through the sequence horizontally

         (let [new-x (+ x 1)
              new-y (y)]))


  ;check for legal inputs to the space

  (for  [num [1 2 3 4 5 6 7 8 9]]

        ;still need these helper functions

         :let [row (row-seq (:board state x y))]
        ;       col (col-seq (:board state x y))
        ;       3x3 (3x3-seq (:board state x y))]

        ; :when (legal? row)
        ; :when (legal? col)
        ; :when (legal? 3x3)]

  (->State (add (:board state) (:curr-position  start-state) num numBank) [new-x new-y])))







