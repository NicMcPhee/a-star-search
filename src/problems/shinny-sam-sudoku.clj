(ns problems.shinny-sam-sudoku)

(defrecord State [board curr-position])

(defn legal? [board-size n]
  (and (<= 0 n) (< n board-size)))

(def values (set (range 1 10)))

(def rows [:y1 :y2 :y3 :y4 :y5 :y6 :y7 :y8 :y9])

(def cols [:x1 :x2 :x3 :x4 :x5 :x6 :x7 :x8 :x9])

(def filled-spaces [])




;the range of number(1-9) to put into the board
(def numBank (range 1 10))

;put numbers into the current position of the board
(defn add [board position number numBank]
  (let [num (nth numBank (- number 1))]
    (assoc-in board position num)))


;###################example########################
(add [[0 1 2 0 0 0 0 0 0]
      [3 4 5 0 0 0 0 0 0]
      [6 7 8 0 0 0 0 0 0]

      [0 1 2 0 0 0 0 0 0]
      [3 4 5 0 0 0 0 0 0]
      [6 7 8 0 0 0 0 0 0]

      [0 1 2 0 0 0 0 0 0]
      [3 4 5 0 0 0 0 0 0]
      [6 7 8 0 0 0 0 0 0]] [0 8] 4 numBank)



(def start-state (->State [[0 0 0 2 6 0 7 0 1]
                                  [6 8 0 0 7 0 0 9 0]
                                  [1 9 0 0 0 4 5 0 0]

                                  [8 2 0 1 0 0 0 4 0]
                                  [0 0 4 6 0 2 9 0 0]
                                  [0 5 0 0 0 3 0 2 8]

                                  [0 0 9 3 0 0 0 7 4]
                                  [0 4 0 0 5 0 0 3 6]
                                  [7 0 3 0 1 8 0 0 0]] [0, 0]))

;###################example########################





;update the current position of the board (don't know if I can call the function and return a new position somewhere else)
(defn updatePos [state]
  (let [x (first (:curr-position state))
        y (second (:curr-position state))]

  (if (= second 8)
         (if (= first 8) "Done!" (let [new-x (+ x 1) new-y (- y 8)]))
         (let [new-x (+ x 0) new-y (+ y 1)])
  )
  )
 )


;go through the number 1-9 to put in the current position and produce different childrens of States of the board(haven't implement the legal function yet)
(for [num [1 2 3]
       ]
       (->State (add (:board  start-state)
     (:curr-position  start-state) num numBank) [0 1])
)
