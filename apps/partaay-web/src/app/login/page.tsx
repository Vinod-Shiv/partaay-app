"use client";

import Link from "next/link";
import { FormEvent, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import { API_BASE_URL } from "@/config/api";

export default function LoginPage() {
  const router = useRouter();
  const searchParams = useSearchParams();

  const registered =
    searchParams.get("registered") === "true";

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (
    e: FormEvent<HTMLFormElement>
  ) => {
    e.preventDefault();

    setError("");
    setLoading(true);

    try {
      const response = await fetch(
        `${API_BASE_URL}/auth/login`,
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
          },

          // Required because Spring Boot authentication
          // uses an HttpOnly session cookie.
          credentials: "include",

          body: JSON.stringify({
            email,
            password,
          }),
        }
      );

      if (!response.ok) {
        const data = await response
          .json()
          .catch(() => null);

        setError(
          data?.message ||
            "Invalid email or password."
        );

        return;
      }

      const user = await response.json();

      console.log("Logged in user:", user);

      // Successful login
      router.push("/dashboard");

    } catch (error) {
      console.error("Login error:", error);

      setError(
        "Unable to connect to Partaay. Please try again."
      );

    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="auth-page">
      <div className="auth-card">

        <Link href="/" className="brand">
          Partaay
        </Link>

        <h1>Welcome back 🎉</h1>

        <p className="subtitle">
          Sign in to discover events, parties,
          and experiences near you.
        </p>

        {registered && (
          <div className="success-alert">

            <span className="success-alert-icon">
              ✓
            </span>

            <div>
              <strong>
                Account created successfully! 🎉
              </strong>

              <p>
                Please sign in using your new account.
              </p>
            </div>

          </div>
        )}

        {error && (
          <div className="error-alert">
            {error}
          </div>
        )}

        <div className="social-buttons">

          <button
            type="button"
            className="social-button"
            onClick={() =>
              alert(
                "Google login will be connected later."
              )
            }
          >
            Continue with Google
          </button>

          <button
            type="button"
            className="social-button"
            onClick={() =>
              alert(
                "Facebook login will be connected later."
              )
            }
          >
            Continue with Facebook
          </button>

        </div>

        <div className="divider">
          <span>or continue with email</span>
        </div>

        <form onSubmit={handleSubmit}>

          <div className="form-group">

            <label htmlFor="email">
              Email address
            </label>

            <input
              id="email"
              type="email"
              placeholder="you@example.com"
              value={email}
              onChange={(e) =>
                setEmail(e.target.value)
              }
              autoComplete="email"
              required
            />

          </div>

          <div className="form-group">

            <label htmlFor="password">
              Password
            </label>

            <div className="password-field">

              <input
                id="password"
                type={
                  showPassword
                    ? "text"
                    : "password"
                }
                placeholder="Enter your password"
                value={password}
                onChange={(e) =>
                  setPassword(e.target.value)
                }
                autoComplete="current-password"
                required
              />

              <button
                type="button"
                className="show-password"
                onClick={() =>
                  setShowPassword(!showPassword)
                }
              >
                {showPassword
                  ? "Hide"
                  : "Show"}
              </button>

            </div>

          </div>

          <div className="form-options">

            <label className="remember">

              <input type="checkbox" />

              Remember me

            </label>

            <Link href="/forgot-password">
              Forgot password?
            </Link>

          </div>

          <button
            type="submit"
            className="primary-button"
            disabled={loading}
          >
            {loading
              ? "Signing In..."
              : "Sign In"}
          </button>

        </form>

        <p className="bottom-text">

          New to Partaay?{" "}

          <Link href="/register">
            Create an account
          </Link>

        </p>

      </div>
    </main>
  );
}