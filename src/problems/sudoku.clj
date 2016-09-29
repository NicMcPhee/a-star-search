(ns problems.sudoku
  (:require [clojure.tools.namespace.repl :refer [refresh]]))


(defn is-distinct?
  "Returns true if no two of the non-zero arguments are ="
  [& more]
  (let [more (filter #(not= 0 %) more)
        old (count more)
        set (into #{} more)
        new (count set)]
    (= new old)))

;;## validation top level ##
(defn every-element-in-vec-distinct?
 "Returns True if every element in every vector is unique or nil.
  Returns False otherwise."
  [vecs]
 (every? identity (map #(apply is-distinct? %) vecs)))

(def every-vector-valid? (memoize every-element-in-vec-distinct?))

;;## validating 3x3 blocks ##
;;the basic idea here is that you can calculate the indexes of a 3x3 sudoku
;;given the identifier of the block you want to get. Selecting offsets for the
;;that turned out to be hard given the conception of state as a single vector.
;;We were able to handle rows of 3x3 blocks, but jumping rows introduced a large
;;number of edge cases. To avoid this, we simply treated the state as
;;3 disconnected rows.

(defn hop [adjusted-n k chunk]
  "Given the first number in the block (n), and the offset (k), returns the 3
  numbers that make up a column in a sudoku block."
  (take-nth 9 (drop (+ adjusted-n k) chunk)))

(defn hop-block [chunk n]
  "Gets the three columns in a 3x3 sudoku block."
  (let [adjusted-n (* n 3)] ;; the upper left hand number in a sudoku block.
    (into [] (concat (hop adjusted-n 0 chunk)
                     (hop adjusted-n 1 chunk)
                     (hop adjusted-n 2 chunk)))))

(defn get-blocks [chunk]
  "Operates on a 3x9 chunk of a sudoku state. Returns a list of 3 vectors of
  9 elements that represents a single block (3x3) in sudoku."
  (map #(hop-block chunk %) [0 1 2])) ;; the 'numbers' of the three blocks

(defn blocks-valid? [state]
  "Checks that every 3x3 sudoku block has contains distinct elements or nil."
  (every-vector-valid? (mapcat get-blocks (partition 27 state))))

;;validate horizontal
(defn horizontal-valid?
 "Check that each row contains distinct elements"
 [state]
 (every-vector-valid? (partition 9 state)))

;;validate vertical
(defn do-vertical-valid [i state]
  (take-nth 9 (drop i state)))

(defn vertical-valid? [state]
  (every-vector-valid? (map #(do-vertical-valid % state) (range 9))))

;;all together now
(defn valid-state?
  "does the validation"
  [state] (and (blocks-valid? state)
               (horizontal-valid? state)
               (vertical-valid? state)))


(defn make-next-states
  "returns a list of states"
  [state] (let [front (take-while #(< 0 %) state)
                back (drop (+ 1 (count front)) state)]
            (map #(concat front [%] back) (range 1 10))))

;; children
(defn children
  "makes children states"
  [state] (filter valid-state? (make-next-states state)))

(defn print-state
  "pretty prints sudoku states in a sane way"
  [state]
  (loop [lst (partition 9 state)]
    (if (empty? lst)
      (println "-=======-")
      (do (println (first lst))
          (recur (rest lst))))))
    
;; heuristics
; the multiplier is 22 (or 21, oddly), where increasing the value no longer
;  improves performance.
; the average of a complete board is 5.
(defn heuristic
 "An easy to implement, but slightly dubious heuristic for sudoku: how far is the
 board average from 5, the average of a complete board. Multiplied by the magic
 constant 22."
  [_ next]
  (* 22 (- 5 (/ (reduce + next) (count next)))))

(defn null-heuristic
  "Takes two arguments and almost certainly returns five."
  [_ next]
  5)
