(ns problems.sudoku-Leuni)


;Our state is only the current board
(defrecord State [board current-pos])

(def start-state (->State [[0 7 5 0 9 0 0 0 6]
                           [0 2 3 0 8 0 0 4 0]
                           [8 0 0 0 0 3 0 0 1]
                           [5 0 0 7 0 2 0 0 0]
                           [0 4 0 8 0 6 0 2 0]
                           [0 0 0 9 0 1 0 0 3]
                           [9 0 0 4 0 0 0 0 7]
                           [0 6 0 0 7 0 5 8 0]
                           [7 0 0 0 1 0 3 9 0]]

                           [8 7]))


(defn all-pos [state]
  (for [x (range 9)] (if (> (get (get (:board state) (first (:current-pos state))) (second (:current-pos state))) 0)
                       (State. (:board state) (if (= (first (:current-pos state)) 8) [0 (+ 1 (second (:current-pos state)))] [(+ 1 (first (:current-pos state))) (second (:current-pos state))] ))
                       (State. (assoc (:board state) (first (:current-pos state)) (assoc (get (:board state) (first (:current-pos state)))  (second (:current-pos state) ) (+ 1 x)))
                             (if (and (= (first (:current-pos state)) 8) (= (second (:current-pos state)) 8)) [8 8]
                               (if (= (first (:current-pos state)) 8) [0 (+ 1 (second (:current-pos state)))] [(+ 1 (first (:current-pos state))) (second (:current-pos state))]))))))


; Function   : check-square
;
; Description: Given a state [board current-pos] this
;              function will determine if there are any duplicate values
;              in the current-pos ([row, col]) square.
;(legal? start-state)
; Returns    : true  - if no duplicates (all elements are distinct)
;              false - if duplicates (all elements are not distinct)
(defn check-square[state pos]
  (let [board (:board state)
        cur-row (first pos)
        cur-col (second pos)]

      ; Determine the appropriate 'square column(legal? start-state)'
      ; by using which column is the current column
      (def col-square (if (<= cur-col 2) 0
                        (if (>= cur-col 6) 2
                          1)))

      ; Determine the appropriate 'square row'
      ; by using which column is the current column
      (def row-square (if (<= cur-row 2) 0
                        (if (>= cur-row 6) 2
                          1)))

      ; Determine the index of the upper-left
      ; corner of the current square
      (def row-square-corner (* row-square 3))
      (def col-square-corner (* col-square 3))

      ; Pull each row of the board that contains
      ; the current square
      (def first-row (get board row-square-corner))

      (def second-row (get board (+ row-square-corner 1)))

      (def third-row (get board (+ row-square-corner 2)))

      ; Partition those rows to get the square
      (def first-row-partition (subvec first-row col-square-corner (+ col-square-corner 3)))

      (def second-row-partition (subvec second-row col-square-corner (+ col-square-corner 3)))

      (def third-row-partition (subvec third-row col-square-corner (+ col-square-corner 3)))

      ; Build the square from the partitions
      (def square (vec (concat first-row-partition second-row-partition third-row-partition)))

      ; Determine if the square has no non-zero duplicates
      (apply distinct? (filter #(> % 0) square))


  ))


; Function   : check-row
;
; Description: Given a state [board current-pos] this
;              function will determine if there are any duplicate values
;              in the current-pos ([row, col]) row value.
;
; Returns    : true  - if no duplicates (all elements are distinct)
;              false - if duplicates (all elements are not distinct)
(defn check-row[state pos]
  (let [board (:board state)
        cur-pos pos]

    ; Get the current row index from the current-pos
    (def row-index (first cur-pos))

    ; Get the current row collection by using the row index
    (def row (get board row-index))

    ; Determine if there are no duplicate values
    ; that are greater than zero
    (apply distinct? (filter #(> % 0) row))

  ))


; Function   : check-column
;
; Description: Given a state [board(legal? start-state)
;              function will determine if there are any duplicate values
;              in the current-pos ([row, col]) column value.
;
; Returns    : true  - if no duplicates (all elements are distinct)
;              false - if duplicates (all elements are not distinct)
(defn check-column [state pos]
  (let [board (:board state)
        cur-pos pos]

    ; Pull the values from the current column
    ; and store them in a vector
    (def column (map #(nth % (second cur-pos)) board))

    ; Determine if there are no duplicate values
    ; that are greater than zero
    (apply distinct? (filter #(> % 0) column))

  ))

(defn isLegal? [state old-pos]
  (and (check-row state old-pos) (check-column state old-pos) (check-square state old-pos)))

(defn children [state]
  (let [old-pos (:current-pos state)]
    (filter #(isLegal? % old-pos) (all-pos state))
        )
  )


