"use client";
import Link from "next/link";
import { FormEvent, useState } from "react";

export default function ForgotPasswordPage() {
  const [submitted, setSubmitted] = useState(false);
  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setSubmitted(true);
  };
  return (
    <main className="auth-page">
      <div className="auth-card">
        <Link href="/" className="brand">
          Partaay
        </Link>
        {!submitted ? (
          <>
            <h1>Forgot your password?</h1>
            <p className="subtitle">
              Enter your email address and we'll help you reset your password.
            </p>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="email">Email address</label>
                <input
                  id="email"
                  type="email"
                  placeholder="you@example.com"
                  required
                />
              </div>
              <button type="submit" className="primary-button">
                Send Reset Link
              </button>
            </form>
            <p className="bottom-text">
              <Link href="/login">← Back to sign in</Link>
            </p>
          </>
        ) : (
          <div className="success-message">
            <div className="success-icon">✓</div>
            <h1>Check your email</h1>
            <p>
              If an account exists for that email address, you'll receive
              password reset instructions.
            </p>
            <p className="prototype-note">
              This is currently a prototype. No email has been sent yet.
            </p>
            <Link href="/login" className="primary-link">
              Back to Sign In
            </Link>
          </div>
        )}
      </div>
    </main>
  );
}
