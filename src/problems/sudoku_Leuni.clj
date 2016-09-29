;Skye Antinozzi and Mitch Finzel

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

                           [2 1]))

(def goal-state (->State [       [1 7 5 2 9 4 8 3 6]
                                 [6 2 3 1 8 7 9 4 5]
                                 [8 9 4 5 6 3 2 7 1]
                                 [5 1 9 7 3 2 4 6 8]
                                 [3 4 7 8 5 6 1 2 9]
                                 [2 8 6 9 4 1 7 5 3]
                                 [9 3 8 4 2 5 6 1 7]
                                 [4 6 1 3 7 9 5 8 2]
                                 [7 5 2 6 1 8 3 9 4]] [8 8]))



; Function   : all-pos (All Possible)
;
; Description: Given a state [board current-pos] this
;              function will check the current position.
;              If it is non-zero it returns a child with an
;              increment to its current-pos and no board changes.
;              If it is zero it will return children with the zero
;              replaced by a number 1 through 9 with incremented current-pos.
;
; Returns    : 9 children with incremented current-pos if the current-pos is non zero
;            : 9 children with the numbers 1-9 in current-pos. Also increments current-pos
(defn all-pos [state]
  ;Temporarily stores the board and row, column information
  (let [board (:board state)
        x-pos (first(:current-pos state))
        y-pos (second(:current-pos state))]

    ;For numbers 1 through 9, x, create a new state with the number at current-pos changed to the new number x + 1
    ;If current-pos is non-zero create children that have the same board and incremented current-pos
    (for [x (range 9)] (if (> (get (get board x-pos) y-pos) 0)
                         (State. board (if (= x-pos 8) [0 (+ 1 y-pos)] [(+ 1 x-pos) y-pos] ))

                         ;Else change the number at curret-pos to x+1
                         (State. (assoc board x-pos (assoc (get board x-pos)  y-pos (+ 1 x)))

                                 ;If current-pos is [8 8] don't increment, return the same current-pos with the new board
                                 (if (and (= x-pos 8) (= y-pos 8)) [8 8]

                                   ;Else if current-pos x-pos is 8 set current-pos to [0 (y-pos + 1)
                                   (if (= x-pos 8) [0 (+ 1 y-pos)] [(+ 1 x-pos) y-pos])))))))


; Function   : check-square
;
; Description: Given a state [board current-pos] this
;              function will determine if there are any duplicate values
;              in the current-pos ([row, col]) square.
;
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
; Description: Given a state [board current-pos]
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

; Function   : isLegal?
;
; Description: Given a state [board current-pos] and old-pos
;              tests the logical And of the three tests
;              check-row, check-column and check-square.
;
; Returns    : true  - if no duplicates (all elements are distinct in rows,columns and squares)
;              false - if duplicates (all elements are not distinct within rows columns and squares)
(defn isLegal? [state old-pos]
  (and (check-row state old-pos) (check-column state old-pos) (check-square state old-pos)))

; Function   : children
;
; Description: Given a state [board current-pos]
;              creates all possible children and
;              then filters them based on their legality
;
; Returns    : All legal children
(defn children [state]
  (let [old-pos (:current-pos state)]
    (filter #(isLegal? % old-pos) (all-pos state))
        )
  )

; Function    : get-previous-position
;
; Description : Takes the current-pos from a state and returns the previous position
(defn get-previous-position [state]
  (let [cur-pos (:current-pos state)
        cur-row (get cur-pos 0)
        cur-col (get cur-pos 1)]

    (if (and (= cur-row 0) (= cur-col 0)) cur-pos
      (if (= cur-col 0) [(dec cur-row) 8]
          [cur-row (dec cur-col)]))

  )

)

; Function    : num-wrong
;
; Description : A heuristic for determining how many values are wrong in the current working row.
;               The program should pursue Sudoku boards with the least number of incorrectly
;               placed values.
(defn num-wrong [goal-state current-state]



  (let [cur-pos (get-previous-position current-state)
        cur-vec (get (:board current-state) (get cur-pos 0))
        goal-vec (get (:board goal-state) (get cur-pos 0))
        ]

      (* 10 (count
        (filter identity
                (map not= cur-vec goal-vec))))

    )

  )




(num-wrong goal-state start-state)


