package model;

public class Pair<L, R> {
	private L l;
	private R r;
	/**
	 * Simple pair constructor
	 * @param l - left value
	 * @param r - right value
	 */
	public Pair(L l, R r) {
		this.l = l;
		this.r = r;
	}
	/**
	 * gets left value
	 * @return left value
	 */
	public L getL() {
		return l;
	}
	/*
	 * gets right value
	 * @return right value
	 */
	public R getR() {
		return r;
	}
	/**
	 * sets left value
	 * @param l - left value
	 */
	public void setL(L l) {
		this.l = l;
	}
	/**
	 * sets right value
	 * @param l - right value
	 */
	public void setR(R r) {
		this.r = r;
	}
	/**
	 * tostring for printing pairs
	 * @return String to print
	 */
	public String toString() {
		return l + " " + r;
	}
}
