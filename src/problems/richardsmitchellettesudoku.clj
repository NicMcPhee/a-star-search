(ns problems.richardsmitchellettesudoku)

;creates our state, which contains the component board, in order to access the board, you must call it (:board statename)
(defrecord State [board])

;this returns a section of the sudoku board that the box is location in. For instance, box 0 will return a vector of boxes 0, 1 and 2
;box value 8 will return boxes 6,7 and 8 in a vector
(defn getSection [board box] (nth board (int (/ box 3))))
;getBox takes in the board and a box, it then returns the box (boxes are 0, 1, 2, 3, 4, 5, 6, 7 and 8)
;box is an integer designating the location of the box that will be returned.
;putting in 1 for the box will return a vector of form [[],[],[]]
(defn getBox [board box] (nth (nth board (int (/ box 3))) (rem box 3)))

;getBoxRow returns the row inside a box, each box has rows 0, 1, and 2. So returning boxRow 1 of box 7 is the same as returning
;the values in box 7th on the 7th overall row
(defn getBoxRow [board box boxRow] (nth
                               (nth
                                 (nth board (int (/ box 3)))
                                    (rem box 3))
                                      boxRow))

;getByBox takes in the board, the box, and the row and column within the box (value 0, 1 or 2) and returns the value at that location
(defn getByBox [board box boxRow boxCol] (nth
                                           (nth
                                             (nth
                                               (nth board (int (/ box 3)))
                                             (rem box 3))
                                           boxRow)
                                         boxCol))
;getByRowCol takes in the board, a row (can be values 0 through 8 inclusive) and a column (values 0 through 8 inclusive) and returns the
;value at that location
(defn getByRowCol [board row col] (getByBox board (+ (* 3 (int (/ row 3))) (int (/ col 3))) (rem row 3) (rem col 3)))

;getBoxValues takes in the board and box and then returns a list of the rows of that box in vector form
;the purpose is to make it easier to check all the values in the box for what they contain
(defn getBoxValues [board box] (into [] (concat (nth (getBox board box) 0) (nth (getBox board box) 1) (nth (getBox board box) 2))))
;checkBox takes in a board, a box and a new-value and returns true if the new-value is not located in the box and false if it is
(defn checkBox [board box new-value] (not= (some #{new-value} (getBoxValues board box)) new-value))
;checkRow iterates through every value in the row
;returns true if the value is not located in the row and false if it is
(defn checkRow [board row col new-value] (if (= (getByRowCol board row col) new-value) false (if (< col 8) (checkRow board row (+ col 1) new-value) true)))
;checkCol iterates through every value in the col
;returns true if the value is not located in the col and false if it is
(defn checkCol [board row col new-value] (if (= (getByRowCol board row col) new-value) false (if (< row 8) (checkCol board (+ row 1) col new-value) true)))

;legal? takes in the board, the row of the spot being changed, the column of the spot being changed and value that that spot could potentially be changed to
;it then checks if the value is in the spot's box, row, or col. If it's in none of these, legal? returns true
(defn legal? [board row col new-value] (and (checkBox board (+ (* 3 (int (/ row 3))) (int (/ col 3))) new-value) (checkRow board row 0 new-value) (checkCol board 0 col new-value)))

;iterator goes through every spot on the board started at row and col until it finds a zero.
;when it finds a zero it returns a vector of [row col] which tells us which spot
;this function is done to find the next empty spot
(defn iterator [board row col] (if (= (getByRowCol board row col) 0)
                                 [row col]
                                 (if (< col 8)
                                   (iterator board row (+ col 1))
                                   (if (< row 8)
                                     (iterator board (+ row 1) 0))
                                 )))



; gets row and col of element to be changed (the first zero)
(defn getToChange [board] (iterator board 0 0))

;this function takes in board, box, bowRow, boxCol and new-value and changes the spot identified by box, boxRow and boxCol to new-value
(defn changeValue-helper [board box boxRow boxCol new-value] (assoc board (int (/ box 3))
                                                               (assoc (getSection board box) (rem box 3)
                                                                 (assoc (getBox board box) boxRow
                                                                   (assoc (getBoxRow board box boxRow) boxCol new-value)))))

;changeValue takes in a row and col and then converts row and col to box, boxRow, boxCol form and uses those values to call its helper
(defn changeValue [board row col new-value] (changeValue-helper board (+ (* 3 (int (/ row 3))) (int (/ col 3))) (rem row 3) (rem col 3) new-value))

; helper functions for heuristic
(defn getBoxSansNum [board new-value]
  (for [incr (range 0 9)
        :let [box incr]
        :when (checkBox board box new-value)]
        box
    ))





(defn eliminateRowsHelper [board row new-value])

(defn eliminateRows [board box new-value])

(defn eliminateColsHelper [board col new-value])

(defn eliminateCols [board box new-value])

(defn combinations [])

; our heuristic
(defn heuristic [board row col new-value] ())

(defn children [state]
(let [board (:board state)
      row (first (getToChange (:board state)))
      col (second (getToChange (:board state)))]
  (for [incr (range 1 10)
        :let [new-value incr]
        :when (legal? board row col new-value)]
    (->State (changeValue board row col new-value)))))
