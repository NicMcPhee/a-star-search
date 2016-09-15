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

                           [0 0]))




(def goal-state (->State [[1 7 5 2 9 4 8 3 6]
                          [6 2 3 1 8 7 9 4 5]
                          [8 9 4 5 6 3 2 7 1]
                          [5 1 9 7 3 2 4 6 8]
                          [3 4 7 8 5 6 1 2 9]
                          [2 8 6 9 4 1 7 5 3]
                          [9 3 8 4 2 5 6 1 7]
                          [4 6 1 3 7 9 5 8 2]
                          [7 5 2 6 1 8 3 9 4]]

                          [8 8]))


; Function   : check-square
;
; Description: Given a state [board current-pos] this
;              function will determine if there are any duplicate values
;              in the current-pos ([row, col]) square.
;
; Returns    : true  - if no duplicates (all elements are distinct)
;              false - if duplicates (all elements are not distinct)
(defn check-square[state]
  (let [board (:board state)
        cur-row (first (:current-pos state))
        cur-col (second (:current-pos state))]

      ; Determine the appropriate 'square column'
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

      (println "You are in [" row-square-corner ", " col-square-corner "].")

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
      (legal? start-state)

(defn legal? [state]

  (and (check-square state) (check-row state) (check-column state))

  )
        ))



; Function   : check-row
;
; Description: Given a state [board current-pos] this
;              function will determine if there are any duplicate values
;              in the current-pos ([row, col]) row value.
;
; Returns    : true  - if no duplicates (all elements are distinct)
;              false - if duplicates (all elements are not distinct)
(defn check-row[state]
  (let [board (:board state)
        cur-pos (:current-pos state)]

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

(defn legal? [state]

  (and (check-square state) (check-row state) (check-column state))

  ) current-pos] this
;              function will determine if there are any duplicate values
;              in the current-pos ([row, col]) column value.
;
; Returns    : true  - if no duplicates (all elements are distinct)
;              false - if duplicates (all elements are not distinct)
(defn check-column [state]
  (let [board (:board state)
        cur-pos (:current-pos state)]

    ; Pull the values from the current column
    ; and store them in a vector
    (def column (map #(nth % (second cur-pos)) board))

    ; Determine if there are no duplicate values
    ; that are greater than zero
    (apply distinct? (filter #(> % 0) column))

  ))


